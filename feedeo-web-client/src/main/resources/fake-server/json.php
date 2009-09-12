<?php
//sleep(1);
header('Content-type:application/json');
if(file_exists($_REQUEST['page'].'.json')) readfile($_REQUEST['page'].'.json');
else echo '{success:false,error:"File not found"}';

?>
