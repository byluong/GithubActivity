## Final Project

Make sure your GithubQuerier works. It should query the first 10 push events, even they aren't on the first page (needs pagination support).

Here are some useful docker commands:
```
docker images
docker build -t <imagename> .
docker-compose up
docker ps -a
docker rm -f $(docker ps -a -q)
```
##### Running dockers images:
```
docker run -d -P <image>
docker run --name <imagename> -d -P <image>
```
##### Navigating to the webpage:
`docker ps -a` and find the port number `0.0.0.0:xxxxx`.
`docker-machine ip default`. 
Mine is `192.168.99.100`, so append the port number: `192.168.99.100:xxxxx`.

This should take you to the tomcat page, so just append `/activity/` to the url.
Make sure it works!

You should have 2 docker images, by the way. One should be called `activityold`, which you built with the old `activity.war` a long time ago. Hitting build in IntelliJ overwrites the `activity.war` file, so once you get your GithubQuerier v2 working, build a new image called `activitynew` with the updated war file.

##### nginx
In the nginx folder, run `./dorun.sh`. This script calls `docker-compose up` for you, starts a local network called `ecs189`, and runs the `init.sh` file inside the nginx container.

##### swapping, some useful commands:
```
docker run -d -P --network ecs189_default --name web2 activitynew
docker exec ecs189_proxy_1 /bin/bash /bin/swap2.sh
docker exec ecs189_proxy_1 cat /etc/nginx/nginx.conf
```

Check the readme for more info!
