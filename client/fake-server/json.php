<?php
sleep(1);
header('Content-type:application/json');
if(file_exists($_GET['page'].'.json')) readfile($_GET['page'].'.json');
else echo '{success:false,error:"File not found"}';

?>
