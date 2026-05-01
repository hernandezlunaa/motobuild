function setTheme(theme, animate = false) {
    document.documentElement.setAttribute("data-bs-theme", theme);
    localStorage.setItem("theme", theme);

    const button = document.getElementById("themeToggle");
    const icon = document.getElementById("themeIcon");

    if (!button || !icon) return;

    if (animate) {
        icon.classList.remove("theme-icon-animate");
        void icon.offsetWidth;
        icon.classList.add("theme-icon-animate");
    }

    if (theme === "dark") {
        icon.src = "/images/ui/sun.svg";
        icon.alt = "Switch to light mode";
        button.title = "Switch to light mode";
    } else {
        icon.src = "/images/ui/moon.png";
        icon.alt = "Switch to dark mode";
        button.title = "Switch to dark mode";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const savedTheme = localStorage.getItem("theme") || "light";
    setTheme(savedTheme);

    const button = document.getElementById("themeToggle");

    if (button) {
        button.addEventListener("click", () => {
            const currentTheme = document.documentElement.getAttribute("data-bs-theme");
            const newTheme = currentTheme === "dark" ? "light" : "dark";
            setTheme(newTheme, true);
        });
    }
});