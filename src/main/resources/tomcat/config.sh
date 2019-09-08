tomcatHome="/usr/dev/apache-tomcat-8.5.45"
sudo groupadd tomcat
sudo useradd -s /bin/false -g tomcat -d $tomcatHome tomcat
sudo chgrp -R tomcat $tomcatHome
sudo chown -R tomcat $tomcatHome
sudo chmod -R 755 $tomcatHome
