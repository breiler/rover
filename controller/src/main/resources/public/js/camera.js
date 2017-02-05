

//
// Shutdown
//
function sys_shutdown() {
  ajax_status.open("GET", "cmd_func.php?cmd=shutdown", true);
  ajax_status.send();
}

function sys_reboot() {
  ajax_status.open("GET", "cmd_func.php?cmd=reboot", true);
  ajax_status.send();
}

//
// MJPEG
//
var mjpeg_img;
var halted = 0;
var previous_halted = 99;
var mjpeg_mode = 0;
var preview_delay = 0;

function reload_img () {
  if(!halted) mjpeg_img.src = "http://10.0.0.34/html/cam_pic.php?time=" + new Date().getTime() + "&pDelay=" + preview_delay;
  else setTimeout("reload_img()", 500);
}

function error_img () {
  setTimeout("mjpeg_img.src = '/rover/img/cam_pic.jpeg'", 10000);
}

function updatePreview(cycle)
{
   if (mjpegmode)
   {
      if (cycle !== undefined && cycle == true)
      {
         mjpeg_img.src = "/rover/img/cam_pic.jpeg";
         setTimeout("mjpeg_img.src = \"cam_pic_new.php?time=\" + new Date().getTime()  + \"&pDelay=\" + preview_delay;", 1000);
         return;
      }

      if (previous_halted != halted)
      {
         if(!halted)
         {
            mjpeg_img.src = "cam_pic_new.php?time=" + new Date().getTime() + "&pDelay=" + preview_delay;
         }
         else
         {
            mjpeg_img.src = "/rover/img/cam_pic.jpeg";
         }
      }
	previous_halted = halted;
   }
}

//
// Ajax Status
//
var ajax_status;

if(window.XMLHttpRequest) {
  ajax_status = new XMLHttpRequest();
}
else {
  ajax_status = new ActiveXObject("Microsoft.XMLHTTP");
}




function reload_ajax (last) {
  ajax_status.open("GET","status_mjpeg.php?last=" + last,true);
  ajax_status.send();
}

function get_zip_progress(zipname) {
   var ajax_zip;
   if(window.XMLHttpRequest) {
      ajax_zip = new XMLHttpRequest();
   }
   else {
      ajax_zip = new ActiveXObject("Microsoft.XMLHTTP");
   }

   ajax_zip.onreadystatechange = function() {
      if(ajax_zip.readyState == 4 && ajax_zip.status == 200) {
         if (process_zip_progress(ajax_zip.responseText)) {
            setTimeout(function() { get_zip_progress(zipname); }, 1000);
         }
         else {
            document.getElementById("zipdownload").value=zipname;
            document.getElementById("zipform").submit();
            document.getElementById("progress").style.display = "none";
         }
      }
   }
   ajax_zip.open("GET","preview.php?zipprogress=" + zipname);
   ajax_zip.send();
}

function process_zip_progress(str) {
   var arr = str.split("/");
   if (str.indexOf("Done") != -1) {
	   return false;
   }
   if (arr.length == 2) {
     var count = parseInt(arr[0]);
     var total = parseInt(arr[1]);
     var progress = document.getElementById("progress");
     var caption = " ";
     if (count > 0) caption = str;
     progress.innerHTML=caption + "<div style=\"width:" + (count/total)*100 + "%;background-color:#0f0;\">&nbsp;</div>";

   }
   return true;
}

//
// Ajax Commands
//
var ajax_cmd;

if(window.XMLHttpRequest) {
  ajax_cmd = new XMLHttpRequest();
}
else {
  ajax_cmd = new ActiveXObject("Microsoft.XMLHTTP");
}

function send_cmd (cmd) {
  ajax_cmd.open("GET","cmd_pipe.php?cmd=" + cmd,true);
  ajax_cmd.send();
}

function update_preview_delay() {
   var video_fps = parseInt(document.getElementById("video_fps").value);
   var divider = parseInt(document.getElementById("divider").value);
   preview_delay = Math.floor(divider / Math.max(video_fps,1) * 1000000);
}

//
// Init
//
function init(mjpeg, video_fps, divider) {

  mjpeg_img = document.getElementById("mjpeg_dest");
  preview_delay = Math.floor(divider / Math.max(video_fps,1) * 1000000);
  if (mjpeg) {
    mjpegmode = 1;
  } else {
     mjpegmode = 0;
     mjpeg_img.onload = reload_img;
     mjpeg_img.onerror = error_img;
     reload_img();
  }
  reload_ajax("");
}