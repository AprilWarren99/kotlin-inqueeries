document.addEventListener("DOMContentLoaded", () => {

  function updateAllSubgroups() {
    document.querySelectorAll("input.categoryToggle").forEach(toggle => {
      const parentWrapper = toggle.closest(".toggleWrapper");
      if (!parentWrapper) return;

      const subGroup = parentWrapper.nextElementSibling;
      if (subGroup && subGroup.classList.contains("categoriesSubgroup")) {
        if (toggle.checked) {
          subGroup.style.display = "flex";
        } else {
          subGroup.style.display = "none";
          subGroup.querySelectorAll("input[type='checkbox']").forEach(input => input.checked = false);
        }
      }
    });
  }

  updateAllSubgroups();

  document.body.addEventListener("change", event => {
    const input = event.target;
    if (!input.matches("input.categoryToggle")) return;

    const parentWrapper = input.closest(".toggleWrapper");
    if (!parentWrapper) return;

    const subGroup = parentWrapper.nextElementSibling;
    if (subGroup && subGroup.classList.contains("categoriesSubgroup")) {
      if (input.checked) {
        subGroup.style.display = "flex";
      } else {
        subGroup.style.display = "none";
        subGroup.querySelectorAll("input[type='checkbox']").forEach(cb => cb.checked = false);
      }
    }
  });

  // Validate current page
  window.validatePage = function(currentPageID) {
    const currentPage = document.getElementById(currentPageID);
    if (!currentPage) return false;

    let valid = true;
    const errors = [];

    // Required fields
    currentPage.querySelectorAll("input[required]").forEach(input => {
      if (!input.value.trim()) {
        valid = false;
        errors.push(`${input.name || "This field"} is required.`);
      }
    });

    // Category subgroups
    currentPage.querySelectorAll(".toggleWrapper").forEach(wrapper => {
      const toggle = wrapper.querySelector("input.categoryToggle");
      if (toggle && toggle.checked) {
        const subGroup = wrapper.nextElementSibling;
        if (subGroup && subGroup.classList.contains("categoriesSubgroup")) {
          const anyChecked = Array.from(subGroup.querySelectorAll("input[type='checkbox']"))
                                  .some(cb => cb.checked);
          if (!anyChecked) {
            valid = false;
            errors.push("Please select at least one option in the subgroup.");
          }
        }
      }
    });

    if (!valid) alert(errors.join("\n"));
    return valid;
  }

  // Next page function (runs validation)
  window.nextFormPage = function(currentPageID, nextPageID) {
    if (!validatePage(currentPageID)) return;

    const currentPage = document.getElementById(currentPageID);
    const nextPage = document.getElementById(nextPageID);
    if (currentPage) currentPage.style.display = "none";
    if (nextPage) nextPage.style.display = "flex";

    const pageTitles = {
      "orgInfo": "Organization Info",
      "contactInfo": "Contact Info",
      "categoriesInfo": "Categories Info",
      "newOrgDisclaimer": "Disclaimer"
    };
    const titleEl = document.getElementById("formPageTitle");
    if (titleEl) titleEl.textContent = pageTitles[nextPageID] || "";

    const bubbles = document.getElementById("progressBubbles");
    if (bubbles) {
      const spans = bubbles.querySelectorAll("span");
      const pageOrder = ["newOrgDisclaimer", "orgInfo", "contactInfo", "categoriesInfo", "accessibilityInfo"];
      spans.forEach((span,index) => {
        span.className = index <= pageOrder.indexOf(nextPageID) ? "filledCircle" : "circle";
      });
    }

    updateAllSubgroups();
  }

  // Previous page function (skips validation)
  window.previousFormPage = function(currentPageID, previousPageID) {
    const currentPage = document.getElementById(currentPageID);
    const prevPage = document.getElementById(previousPageID);
    if (currentPage) currentPage.style.display = "none";
    if (prevPage) prevPage.style.display = "flex";

    const pageTitles = {
      "orgInfo": "Organization Information",
      "contactInfo": "Contact Information",
      "categoriesInfo": "Categories Information",
      "accessibilityInfo": "Accessibility Information",
      "newOrgDisclaimer": "Disclaimer",
    };

    const titleEl = document.getElementById("formPageTitle");
    if (titleEl) titleEl.textContent = pageTitles[previousPageID] || "";

    const bubbles = document.getElementById("progressBubbles");
    if (bubbles) {
      const spans = bubbles.querySelectorAll("span");
      const pageOrder = ["orgInfo", "newOrgDisclaimer", "contactInfo","categoriesInfo"];
      spans.forEach((span,index) => {
        span.className = index <= pageOrder.indexOf(previousPageID) ? "filledCircle" : "circle";
      });
    }

    updateAllSubgroups();
  }

});
