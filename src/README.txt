NOTE : Add the following script to ~/script.sh
    
    #!/bin/bash
    
    nohup ffserver -n -d -f /etc/ffserver.conf & nohup ffmpeg -v quiet -f v4l2 -s 320x240 -r 60 -i /dev/video0  http://localhost:8090/feed1.ffm &

Note : Add the following line to the end of ~/.profile
    ~/script.sh

Note : Run the following 
    
    chmod 777 ~/script.sh

