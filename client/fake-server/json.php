<?php
//sleep(1);
header('Content-type:application/json');
if(file_exists($_POST['page'].'.json')) readfile($_POST['page'].'.json');
else echo '{success:false,error:"File not found"}';

?>
