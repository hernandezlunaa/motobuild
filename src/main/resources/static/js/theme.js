function setTheme(theme) {
    document.documentElement.setAttribute("data-bs-theme", theme);
    localStorage.setItem("theme", theme);

    const button = document.getElementById("themeToggle");
    if (button) {
        button.textContent = theme === "dark" ? "Light Mode" : "Dark Mode";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const savedTheme = localStorage.getItem("theme") || "light";
    setTheme(savedTheme);

    const button = document.getElementById("themeToggle");
    if (button) {
        button.addEventListener("click", () => {
            const currentTheme = document.documentElement.getAttribute("data-bs-theme");
            setTheme(currentTheme === "dark" ? "light" : "dark");
        });
    }
});