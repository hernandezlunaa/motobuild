package com.motobuild.service;

import com.motobuild.model.Build;
import com.motobuild.model.BuildPart;
import com.motobuild.model.Motorcycle;
import com.motobuild.model.Part;
import com.motobuild.model.User;
import com.motobuild.repository.BuildPartRepository;
import com.motobuild.repository.BuildRepository;
import com.motobuild.repository.MotorcycleRepository;
import com.motobuild.repository.PartRepository;
import com.motobuild.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuildService {

    private final BuildRepository buildRepository;
    private final BuildPartRepository buildPartRepository;
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final PartRepository partRepository;
    private final JdbcTemplate jdbcTemplate;

    public BuildService(BuildRepository buildRepository,
                        BuildPartRepository buildPartRepository,
                        UserRepository userRepository,
                        MotorcycleRepository motorcycleRepository,
                        PartRepository partRepository,
                        JdbcTemplate jdbcTemplate) {
        this.buildRepository = buildRepository;
        this.buildPartRepository = buildPartRepository;
        this.userRepository = userRepository;
        this.motorcycleRepository = motorcycleRepository;
        this.partRepository = partRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class WarningAction {
        private final Integer partId;
        private final String label;

        public WarningAction(Integer partId, String label) {
            this.partId = partId;
            this.label = label;
        }

        public Integer getPartId() {
            return partId;
        }

        public String getLabel() {
            return label;
        }
    }

    public static class BuildWarning {
        private final String message;
        private final List<WarningAction> actions;
        private final Integer removeBuildPartId;
        private final String removeLabel;

        public BuildWarning(String message) {
            this.message = message;
            this.actions = new ArrayList<>();
            this.removeBuildPartId = null;
            this.removeLabel = null;
        }

        public BuildWarning(String message,
                            List<WarningAction> actions,
                            Integer removeBuildPartId,
                            String removeLabel) {
            this.message = message;
            this.actions = actions;
            this.removeBuildPartId = removeBuildPartId;
            this.removeLabel = removeLabel;
        }

        public String getMessage() {
            return message;
        }

        public List<WarningAction> getActions() {
            return actions;
        }

        public Integer getRemoveBuildPartId() {
            return removeBuildPartId;
        }

        public String getRemoveLabel() {
            return removeLabel;
        }
    }

    public static class DeletedBuildSnapshot implements Serializable {
        private final String buildName;
        private final String description;
        private final Integer motorcycleId;
        private final List<DeletedBuildPartSnapshot> parts;

        public DeletedBuildSnapshot(String buildName,
                                    String description,
                                    Integer motorcycleId,
                                    List<DeletedBuildPartSnapshot> parts) {
            this.buildName = buildName;
            this.description = description;
            this.motorcycleId = motorcycleId;
            this.parts = parts;
        }

        public String getBuildName() {
            return buildName;
        }

        public String getDescription() {
            return description;
        }

        public Integer getMotorcycleId() {
            return motorcycleId;
        }

        public List<DeletedBuildPartSnapshot> getParts() {
            return parts;
        }
    }

    public static class DeletedBuildPartSnapshot implements Serializable {
        private final Integer partId;
        private final String status;
        private final Integer quantity;
        private final BigDecimal customPrice;
        private final String notes;

        public DeletedBuildPartSnapshot(Integer partId,
                                        String status,
                                        Integer quantity,
                                        BigDecimal customPrice,
                                        String notes) {
            this.partId = partId;
            this.status = status;
            this.quantity = quantity;
            this.customPrice = customPrice;
            this.notes = notes;
        }

        public Integer getPartId() {
            return partId;
        }

        public String getStatus() {
            return status;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public BigDecimal getCustomPrice() {
            return customPrice;
        }

        public String getNotes() {
            return notes;
        }
    }

    public User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
    }

    public List<Build> getBuildsForUser(Integer userId) {
        return buildRepository.findBuildsForUserWithDetails(userId);
    }

    public Build getBuild(Integer buildId, Integer userId) {
        Build build = buildRepository.findBuildWithDetails(buildId)
                .orElseThrow(() -> new RuntimeException("Build not found."));

        if (!build.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You do not have permission to view this build.");
        }

        return build;
    }

    @Transactional
    public Build createBuild(Integer userId, String buildName, Integer motorcycleId, String description) {
        User user = getUser(userId);

        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found."));

        Build build = new Build();
        build.setUser(user);
        build.setMotorcycle(motorcycle);
        build.setBuildName(buildName);
        build.setDescription(description);

        return buildRepository.save(build);
    }

    @Transactional
    public String addPartToBuild(Integer userId, Integer buildId, Integer partId) {
        Build build = getBuild(buildId, userId);

        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new RuntimeException("Part not found."));

        BuildPart existingBuildPart = buildPartRepository
                .findByBuild_BuildIdAndPart_PartId(buildId, partId)
                .orElse(null);

        if (existingBuildPart != null) {
            existingBuildPart.setStatus("planned");
            buildPartRepository.save(existingBuildPart);
            return part.getPartName();
        }

        BuildPart buildPart = new BuildPart();
        buildPart.setBuild(build);
        buildPart.setPart(part);
        buildPart.setStatus("planned");
        buildPart.setQuantity(1);

        buildPartRepository.save(buildPart);

        return part.getPartName();
    }

    @Transactional
    public void updateBuildPartStatus(Integer userId, Integer buildId, Integer buildPartId, String status) {
        getBuild(buildId, userId);

        BuildPart buildPart = buildPartRepository.findById(buildPartId)
                .orElseThrow(() -> new RuntimeException("Build part not found."));

        if (!buildPart.getBuild().getBuildId().equals(buildId)) {
            throw new RuntimeException("Build part does not belong to this build.");
        }

        if (!status.equals("planned") && !status.equals("installed") && !status.equals("removed")) {
            throw new RuntimeException("Invalid status.");
        }

        buildPart.setStatus(status);
        buildPartRepository.save(buildPart);
    }

    @Transactional
    public String removePartFromBuild(Integer userId, Integer buildId, Integer buildPartId) {
        getBuild(buildId, userId);

        List<String> partNames = jdbcTemplate.queryForList(
                """
                SELECT p.part_name
                FROM build_parts bp
                JOIN parts p ON bp.part_id = p.part_id
                WHERE bp.build_id = ?
                AND bp.build_part_id = ?
                """,
                String.class,
                buildId,
                buildPartId
        );

        if (partNames.isEmpty()) {
            throw new RuntimeException("Build part not found or already removed.");
        }

        String partName = partNames.get(0);

        int rowsDeleted = jdbcTemplate.update(
                """
                DELETE FROM build_parts
                WHERE build_id = ?
                AND build_part_id = ?
                """,
                buildId,
                buildPartId
        );

        if (rowsDeleted == 0) {
            throw new RuntimeException("Build part not found or already removed.");
        }

        return partName;
    }

    public BigDecimal calculateTotalCost(Build build) {
        if (build.getBuildParts() == null) {
            return BigDecimal.ZERO;
        }

        return build.getBuildParts()
                .stream()
                .filter(buildPart -> !"removed".equals(buildPart.getStatus()))
                .map(BuildPart::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateCartCost(Build build) {
        if (build.getBuildParts() == null) {
            return BigDecimal.ZERO;
        }

        return build.getBuildParts()
                .stream()
                .filter(buildPart -> "planned".equals(buildPart.getStatus()))
                .map(BuildPart::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<BuildWarning> getWarnings(Integer buildId, Integer userId) {
        Build build = getBuild(buildId, userId);
        List<BuildWarning> warnings = new ArrayList<>();

        if (build.getBuildParts() == null || build.getBuildParts().isEmpty()) {
            return warnings;
        }

        for (BuildPart buildPart : build.getBuildParts()) {
            if ("removed".equals(buildPart.getStatus())) {
                continue;
            }

            Part part = buildPart.getPart();
            Integer partId = part.getPartId();
            Integer motorcycleId = build.getMotorcycle().getMotorcycleId();

            List<String> incompatibilityReasons = jdbcTemplate.queryForList(
                    """
                    SELECT reason
                    FROM bike_part_incompatibility
                    WHERE motorcycle_id = ?
                    AND part_id = ?
                    """,
                    String.class,
                    motorcycleId,
                    partId
            );

            if (!incompatibilityReasons.isEmpty()) {
                String reason = incompatibilityReasons.get(0);

                addWarningIfNew(
                        warnings,
                        new BuildWarning(
                                part.getPartName() + " is not compatible with this motorcycle. " + reason,
                                new ArrayList<>(),
                                buildPart.getBuildPartId(),
                                "Remove " + part.getPartName()
                        )
                );
            }

            List<BuildWarning> missingDependencies = jdbcTemplate.query(
                    """
                    SELECT 
                        pd.dependency_group,
                        GROUP_CONCAT(required.part_id ORDER BY required.part_name SEPARATOR ',') AS required_part_ids,
                        GROUP_CONCAT(required.part_name ORDER BY required.part_name SEPARATOR ' or ') AS required_part_names
                    FROM part_dependencies pd
                    JOIN parts required ON pd.required_part_id = required.part_id
                    WHERE pd.part_id = ?
                    GROUP BY pd.dependency_group
                    HAVING SUM(
                        CASE 
                            WHEN pd.required_part_id IN (
                                SELECT bp.part_id
                                FROM build_parts bp
                                WHERE bp.build_id = ?
                                AND bp.status <> 'removed'
                            )
                            THEN 1
                            ELSE 0
                        END
                    ) = 0
                    """,
                    (rs, rowNum) -> {
                        String requiredPartIds = rs.getString("required_part_ids");
                        String requiredPartNames = rs.getString("required_part_names");

                        String[] idArray = requiredPartIds.split(",");
                        String[] nameArray = requiredPartNames.split(" or ");

                        List<WarningAction> actions = new ArrayList<>();

                        for (int i = 0; i < idArray.length; i++) {
                            Integer requiredPartId = Integer.parseInt(idArray[i]);
                            String requiredPartName = nameArray[i];

                            actions.add(new WarningAction(
                                    requiredPartId,
                                    "Add " + requiredPartName
                            ));
                        }

                        return new BuildWarning(
                                part.getPartName() + " requires/recommends one of: " + requiredPartNames + ".",
                                actions,
                                buildPart.getBuildPartId(),
                                "Remove " + part.getPartName()
                        );
                    },
                    partId,
                    buildId
            );

            for (BuildWarning warning : missingDependencies) {
                addWarningIfNew(warnings, warning);
            }

            List<BuildWarning> conflicts = jdbcTemplate.query(
                    """
                    SELECT bp.build_part_id, conflict.part_name
                    FROM part_conflicts pc
                    JOIN parts conflict ON pc.conflicting_part_id = conflict.part_id
                    JOIN build_parts bp ON bp.part_id = pc.conflicting_part_id
                    WHERE pc.part_id = ?
                    AND bp.build_id = ?
                    AND bp.status <> 'removed'
                    """,
                    (rs, rowNum) -> {
                        Integer conflictingBuildPartId = rs.getInt("build_part_id");
                        String conflictingPartName = rs.getString("part_name");

                        return new BuildWarning(
                                part.getPartName() + " conflicts with: " + conflictingPartName + ".",
                                new ArrayList<>(),
                                conflictingBuildPartId,
                                "Remove " + conflictingPartName
                        );
                    },
                    partId,
                    buildId
            );

            for (BuildWarning warning : conflicts) {
                addWarningIfNew(warnings, warning);
            }
        }

        return warnings;
    }

    private void addWarningIfNew(List<BuildWarning> warnings, BuildWarning newWarning) {
        boolean alreadyExists = warnings.stream()
                .anyMatch(existingWarning -> existingWarning.getMessage().equals(newWarning.getMessage()));

        if (!alreadyExists) {
            warnings.add(newWarning);
        }
    }

    @Transactional
    public void updateBuildName(Integer userId, Integer buildId, String buildName) {
        Build build = getBuild(buildId, userId);

        String oldName = build.getBuildName();
        String newName = buildName.trim();

        build.setBuildName(newName);
        buildRepository.save(build);

        logAccountActivity(
                userId,
                "BUILD_RENAMED",
                "Renamed build from " + oldName + " to " + newName
        );
    }

    @Transactional
    public void updateBuildMotorcycle(Integer userId, Integer buildId, Integer motorcycleId) {
        Build build = getBuild(buildId, userId);

        Motorcycle oldMotorcycle = build.getMotorcycle();

        Motorcycle newMotorcycle = motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> new RuntimeException("Motorcycle not found."));

        build.setMotorcycle(newMotorcycle);
        buildRepository.save(build);

        String oldBikeName = oldMotorcycle.getYear() + " " + oldMotorcycle.getMake() + " " + oldMotorcycle.getModel();
        String newBikeName = newMotorcycle.getYear() + " " + newMotorcycle.getMake() + " " + newMotorcycle.getModel();

        logAccountActivity(
                userId,
                "BUILD_BIKE_CHANGED",
                "Changed " + build.getBuildName() + " from " + oldBikeName + " to " + newBikeName
        );
    }

    @Transactional
    public DeletedBuildSnapshot deleteBuild(Integer userId, Integer buildId) {
        Build build = getBuild(buildId, userId);

        List<DeletedBuildPartSnapshot> partSnapshots = new ArrayList<>();

        if (build.getBuildParts() != null) {
            for (BuildPart buildPart : build.getBuildParts()) {
                partSnapshots.add(new DeletedBuildPartSnapshot(
                        buildPart.getPart().getPartId(),
                        buildPart.getStatus(),
                        buildPart.getQuantity(),
                        buildPart.getCustomPrice(),
                        buildPart.getNotes()
                ));
            }
        }

        DeletedBuildSnapshot snapshot = new DeletedBuildSnapshot(
                build.getBuildName(),
                build.getDescription(),
                build.getMotorcycle().getMotorcycleId(),
                partSnapshots
        );

        buildRepository.delete(build);

        jdbcTemplate.update(
                """
                DELETE aa
                FROM account_activity aa
                WHERE aa.user_id = ?
                AND aa.created_at >= DATE_SUB(NOW(), INTERVAL 1 SECOND)
                AND aa.activity_type = 'PART_REMOVED'
                AND aa.activity_message LIKE ?
                """,
                userId,
                "% from " + snapshot.getBuildName()
        );

        return snapshot;
    }

    @Transactional
    public Build restoreDeletedBuild(Integer userId, DeletedBuildSnapshot snapshot) {
        User user = getUser(userId);

        Motorcycle motorcycle = motorcycleRepository.findById(snapshot.getMotorcycleId())
                .orElseThrow(() -> new RuntimeException("Motorcycle not found."));

        Build build = new Build();
        build.setUser(user);
        build.setMotorcycle(motorcycle);
        build.setBuildName(snapshot.getBuildName());
        build.setDescription(snapshot.getDescription());

        Build savedBuild = buildRepository.save(build);

        for (DeletedBuildPartSnapshot partSnapshot : snapshot.getParts()) {
            Part part = partRepository.findById(partSnapshot.getPartId())
                    .orElse(null);

            if (part == null) {
                continue;
            }

            BuildPart buildPart = new BuildPart();
            buildPart.setBuild(savedBuild);
            buildPart.setPart(part);
            buildPart.setStatus(partSnapshot.getStatus());
            buildPart.setQuantity(partSnapshot.getQuantity());
            buildPart.setCustomPrice(partSnapshot.getCustomPrice());
            buildPart.setNotes(partSnapshot.getNotes());

            buildPartRepository.save(buildPart);
        }

        jdbcTemplate.update(
                """
                DELETE aa
                FROM account_activity aa
                WHERE aa.user_id = ?
                AND aa.created_at >= DATE_SUB(NOW(), INTERVAL 1 SECOND)
                AND aa.activity_type IN ('BUILD_CREATED', 'PART_ADDED')
                """,
                userId
        );

        logAccountActivity(
                userId,
                "BUILD_RESTORED",
                "Restored deleted build: " + savedBuild.getBuildName()
        );

        return savedBuild;
    }

    private void logAccountActivity(Integer userId, String activityType, String activityMessage) {
        jdbcTemplate.update(
                """
                INSERT INTO account_activity (user_id, activity_type, activity_message)
                VALUES (?, ?, ?)
                """,
                userId,
                activityType,
                activityMessage
        );
    }
}