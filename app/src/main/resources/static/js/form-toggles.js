document.addEventListener("DOMContentLoaded", () => {
  document.body.addEventListener("change", function (event) {
    const input = event.target;
    if (!input.matches("input.categoryToggle")) return;

    const parentWrapper = input.closest(".toggleWrapper");
    if (!parentWrapper) return;

    const subGroup = parentWrapper.nextElementSibling;
    if (subGroup && subGroup.classList.contains("categoriesSubgroup")) {
      subGroup.style.display = input.checked ? "" : "none";
    }
  });


  // initially check the state of the toggles and show/hide subgroups accordingly
  document.querySelectorAll(".categoryToggle").forEach(toggle => {
    const parentWrapper = toggle.closest(".toggleWrapper");
    if (!parentWrapper) return;

    const subGroup = parentWrapper.nextElementSibling;
    if (subGroup && subGroup.classList.contains("categoriesSubgroup")) {
      // Set visibility based on the toggle state (checked or unchecked)
      subGroup.style.display = toggle.checked ? "" : "none";
    }
  });
});
