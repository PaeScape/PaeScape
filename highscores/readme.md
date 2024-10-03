
# Deployment:
paescape-highscores-api/.env
paescape-highscores-next/.env

pm2 startup
... do the startup stuff
cd paescape-highscores-next
npm i
npm run build
pm2 start npm run start --name "HighScores frontend" 
cd paescape-highscores-api
npm i
node createDB.js
pm2 start npm run start --name "HighScores api" 
pm2 save


# Check if pm2-<USER> service has been added
$ systemctl list-units
# Check logs
$ journalctl -u pm2-<USER>
# Cat systemd configuration file
$ systemctl cat pm2-<USER>

# check the startup file..
[Unit]
Wants=network-online.target
After=network.target network-online.target

[....]

[Install]
WantedBy=multi-user.target network-online.target


nginx for next:
```
server {
        listen 80;
        server_name highscores.paescape.online;
        root /var/www/html;
        index index.html index.htm;

        location / {
                proxy_pass             http://127.0.0.1:3000;
                proxy_read_timeout     60;
                proxy_connect_timeout  60;
                proxy_redirect         off;

                # Allow the use of websockets
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_set_header X-Forwarded-Host $host;
                proxy_set_header X-Forwarded-Port $server_port;
        }
}
```