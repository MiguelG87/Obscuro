var currentPageUrl = window.location.pathname;
var allowedPages = ["/sweetdreams", "/nightmare", "/sleepparalysis"];
if (allowedPages.includes(currentPageUrl)) {
    document.getElementById("homeIcon").style.display = "inline";
} else {
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
