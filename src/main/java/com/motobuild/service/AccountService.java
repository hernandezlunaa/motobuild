package com.motobuild.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final JdbcTemplate jdbcTemplate;

    public AccountService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class AccountStats {
        private final Integer totalBuilds;
        private final Integer totalParts;
        private final Integer plannedParts;
        private final Integer installedParts;
        private final BigDecimal totalBuildValue;
        private final BigDecimal cartValue;
        private final String mostRecentBuild;

        public AccountStats(Integer totalBuilds,
                            Integer totalParts,
                            Integer plannedParts,
                            Integer installedParts,
                            BigDecimal totalBuildValue,
                            BigDecimal cartValue,
                            String mostRecentBuild) {
            this.totalBuilds = totalBuilds;
            this.totalParts = totalParts;
            this.plannedParts = plannedParts;
            this.installedParts = installedParts;
            this.totalBuildValue = totalBuildValue;
            this.cartValue = cartValue;
            this.mostRecentBuild = mostRecentBuild;
        }

        public Integer getTotalBuilds() {
            return totalBuilds;
        }

        public Integer getTotalParts() {
            return totalParts;
        }

        public Integer getPlannedParts() {
            return plannedParts;
        }

        public Integer getInstalledParts() {
            return installedParts;
        }

        public BigDecimal getTotalBuildValue() {
            return totalBuildValue;
        }

        public BigDecimal getCartValue() {
            return cartValue;
        }

        public String getMostRecentBuild() {
            return mostRecentBuild;
        }
    }

    public static class AccountActivity {
        private final String activityType;
        private final String activityMessage;
        private final LocalDateTime createdAt;

        public AccountActivity(String activityType, String activityMessage, LocalDateTime createdAt) {
            this.activityType = activityType;
            this.activityMessage = activityMessage;
            this.createdAt = createdAt;
        }

        public String getActivityType() {
            return activityType;
        }

        public String getActivityMessage() {
            return activityMessage;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }

    public AccountStats getAccountStats(Integer userId) {
        Integer totalBuilds = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM builds WHERE user_id = ?",
                Integer.class,
                userId
        );

        Integer totalParts = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM build_parts bp
                JOIN builds b ON bp.build_id = b.build_id
                WHERE b.user_id = ?
                AND bp.status <> 'removed'
                """,
                Integer.class,
                userId
        );

        Integer plannedParts = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM build_parts bp
                JOIN builds b ON bp.build_id = b.build_id
                WHERE b.user_id = ?
                AND bp.status = 'planned'
                """,
                Integer.class,
                userId
        );

        Integer installedParts = jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM build_parts bp
                JOIN builds b ON bp.build_id = b.build_id
                WHERE b.user_id = ?
                AND bp.status = 'installed'
                """,
                Integer.class,
                userId
        );

        BigDecimal totalBuildValue = jdbcTemplate.queryForObject(
                """
                SELECT COALESCE(SUM(COALESCE(bp.custom_price, p.price) * bp.quantity), 0)
                FROM build_parts bp
                JOIN builds b ON bp.build_id = b.build_id
                JOIN parts p ON bp.part_id = p.part_id
                WHERE b.user_id = ?
                AND bp.status <> 'removed'
                """,
                BigDecimal.class,
                userId
        );

        BigDecimal cartValue = jdbcTemplate.queryForObject(
                """
                SELECT COALESCE(SUM(COALESCE(bp.custom_price, p.price) * bp.quantity), 0)
                FROM build_parts bp
                JOIN builds b ON bp.build_id = b.build_id
                JOIN parts p ON bp.part_id = p.part_id
                WHERE b.user_id = ?
                AND bp.status = 'planned'
                """,
                BigDecimal.class,
                userId
        );

        List<String> recentBuilds = jdbcTemplate.queryForList(
                """
                SELECT build_name
                FROM builds
                WHERE user_id = ?
                ORDER BY created_at DESC
                LIMIT 1
                """,
                String.class,
                userId
        );

        String mostRecentBuild = recentBuilds.isEmpty() ? "No builds yet" : recentBuilds.get(0);

        return new AccountStats(
                totalBuilds == null ? 0 : totalBuilds,
                totalParts == null ? 0 : totalParts,
                plannedParts == null ? 0 : plannedParts,
                installedParts == null ? 0 : installedParts,
                totalBuildValue == null ? BigDecimal.ZERO : totalBuildValue,
                cartValue == null ? BigDecimal.ZERO : cartValue,
                mostRecentBuild
        );
    }

    public List<AccountActivity> getRecentActivity(Integer userId) {
        return jdbcTemplate.query(
                """
                SELECT activity_type, activity_message, created_at
                FROM account_activity
                WHERE user_id = ?
                ORDER BY created_at DESC
                """,
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("created_at");

                    return new AccountActivity(
                            rs.getString("activity_type"),
                            rs.getString("activity_message"),
                            timestamp == null ? null : timestamp.toLocalDateTime()
                    );
                },
                userId
        );
    }
}