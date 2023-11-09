// Get the current page URL
var currentPageUrl = window.location.pathname;

// Specify the pages where you want to display the home icon
var allowedPages = ["/sweetdreams", "/nightmare", "/sleepparalysis"];

// Check if the current page is in the allowedPages array
if (allowedPages.includes(currentPageUrl)) {
    // Show the home icon
    document.getElementById("homeIcon").style.display = "inline";
} else {
    // Hide the home icon
    document.getElementById("homeIcon").style.display = "none";
}


var audio = document.getElementById("audio");
var userAudio = document.getElementById("homeMusic").value;
audio.volume = userAudio;
var audioGame = document.getElementById("audioGame");
var userAudioGame = document.getElementById("gameMusic").value;
audioGame.volume = userAudioGame;
var audioFX = document.getElementById("audioFX");
var userAudioFX = document.getElementById("soundFX").value;
audioFX.volume = userAudioFX;
var brightness = document.getElementById("page");
var userBrightness = document.getElementById("brightness");
brightness.style.filter = `brightness(${currentBrightness})`;
