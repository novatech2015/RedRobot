NOTE : Add the following script to ~/script.sh
    
    #!/bin/bash
    
    nohup ffserver -n -d -f /etc/ffserver.conf & nohup ffmpeg -v quiet -f v4l2 -s 320x240 -r 60 -i /dev/video0  http://localhost:8090/feed1.ffm &

Note : Add the following line to the end of ~/.profile
    ~/script.sh

Note : Run the following 
    
    chmod 777 ~/script.sh

Note : You may also try the following script to stream to twitch. Add the following to .bashrc

     streaming() {
        INRES="640x480" # input resolution
        OUTRES="640x480" # output resolution
        FPS="15" # target FPS
        GOP="30" # i-frame interval, should be double of FPS, 
        GOPMIN="15" # min i-frame interval, should be equal to fps, 
        THREADS="2" # max 6
        CBR="1000k" # constant bitrate (should be between 1000k - 3000k)
        QUALITY="ultrafast"  # one of the many FFMPEG preset
        AUDIO_RATE="44100"
        STREAM_KEY="$1" # use the terminal command Streaming streamkeyhere to stream your video to twitch or justin
        SERVER="live-lax" # twitch server in frankfurt, see http://bashtech.net/twitch/ingest.php for list

        ffmpeg -f /dev/video0 -s "$INRES" -r "$FPS" -i :0.0 -f flv \
          -vcodec libx264 -g $GOP -keyint_min $GOPMIN -b:v $CBR -minrate $CBR -maxrate $CBR -pix_fmt yuv420p\
          -s $OUTRES -preset $QUALITY -tune film -threads $THREADS -strict normal \
          -bufsize $CBR "rtmp://$SERVER.twitch.tv/app/$STREAM_KEY"
    }