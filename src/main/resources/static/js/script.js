function init () {

    let migraineButton = document.getElementById("migraineButton");

    if (Object.values(migraine)[1] == null) {
        window.alert("works");
    }

    migraineButton.addEventListener("click", function() {

        migraineButton.style.backgroundColor = "#a13333";

    });

}

window.onload = init;