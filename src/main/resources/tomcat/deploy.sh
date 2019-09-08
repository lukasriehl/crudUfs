# deploy.sh
# Script to shutdown tomcat and redeploy webapp

#Check the number of arguments
if [ "$#" -ne 2 ]
then
        echo "Missing arguments: webapp name and WAR file location"
        exit 1
fi

if [ -z "$1" ]
then
        echo "First argument cannot be empty"
        exit 1
fi

if [ -z "$2" ]
then
        echo "Second argument cannot be empty"
        exit 1
fi

tomcatHome="/usr/dev/apache-tomcat-8.5.45"
echo "Tomcat home: $tomcatHome"

# Get the process ID of tomcat
pid=$(ps h -C java -o "%p:%a" | grep catalina | cut -d: -f1)
if [ "$pid" > 0 ]
then
        echo "Shutting down tomcat PID $pid"

        # Shutdown tomcat
        #$tomcatHome/bin/shutdown.sh
        sudo kill -9 $pid

        # Wait until tomcat is shutdown
        while sudo kill -0 $pid > /dev/null; do sleep 1; done

fi

# remove the old webapp
echo "Removing webapp $1"
mv $tomcatHome/webapps/$1.war $tomcatHome
rm -rf $tomcatHome/webapps/$1.war

# Copy the new WAR file to the webapps folder
cp $2 $tomcatHome/webapps/$1.war

# Change the permissions
sudo chown tomcat:tomcat $tomcatHome/webapps/$1.war

# Start up tomcat
/etc/init.d/tomcat start

# Finished
echo "redeployed successfully"
