document.addEventListener("DOMContentLoaded", function () {
    const editUserModal = document.getElementById("editUserModal");
    const closeBtn = document.querySelector(".close-btn");
    const editUserForm = document.getElementById("editUserForm");

    const editButtons = document.querySelectorAll(".edit-btn");
    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            const userId = this.getAttribute("data-id");
            const userName = this.getAttribute("data-name");
            const userEmail = this.getAttribute("data-email");
            const userRole = this.getAttribute("data-role");
            const userEnabled = this.getAttribute("data-enabled") === "true";

            document.getElementById("editUserId").value = userId;
            document.getElementById("editUserName").value = userName;
            document.getElementById("editUserEmail").value = userEmail;
            document.getElementById("editUserRole").value = userRole;
            document.getElementById("editUserEnabled").checked = userEnabled;

            editUserModal.style.display = "flex";
        });
    });

    closeBtn.addEventListener("click", function () {
        editUserModal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target === editUserModal) {
            editUserModal.style.display = "none";
        }
    });

    editUserForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(editUserForm);

        fetch(editUserForm.getAttribute("action"), {
            method: "POST",
            body: formData,
        })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                } else {
                    alert("Failed to update user.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("An error occurred. Please try again.");
            });
    });
});
