document.addEventListener("DOMContentLoaded", function () {

    fetch("header.html")
        .then((response) => response.text())
        .then((data) => {
            document.getElementById("header-placeholder").innerHTML = data;
        });

    fetch("footer.html")
        .then((response) => response.text())
        .then((data) => {
            document.getElementById("footer-placeholder").innerHTML = data;
        });
});

function toggleFAQ(element) {
    const faqAnswer = element.nextElementSibling;
    const isOpen = faqAnswer.style.display === "block";

    document.querySelectorAll(".faq-answer").forEach(answer => {
        answer.style.display = "none";
    });
    document.querySelectorAll(".faq-question").forEach(question => {
        question.classList.remove("active");
    });


    if (!isOpen) {
        faqAnswer.style.display = "block";
        element.classList.add("active");
    }
}