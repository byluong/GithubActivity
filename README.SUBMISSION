## Final Project
How to use:
1. Build Docker image with name `activityold` from original forked `activity.war`.
2. Build Docker image with name `activitynew` using my updated `activity.war`, located in `activity` directory.
2. Build Docker image with name `ng`, located in `nginx` directory. 
3. `./dorun.sh`
4. Navigate to `localhost:8888` to see the old GithubQuerier. If localhost doesn't work, replace it with whatever `docker-machine ip default` returns.

### Hotswapping
Usage:
`hotswap.sh <image>`
- Runs a new docker container with the specified image
- Calls appropriate swap.sh files to edit the nginx proxy config
- Kills previous container
- Refresh `localhost:8888` to see the new page!

Uses `PREV=$(docker ps -a -f "name=web" | grep -oh "\w*web\w")` to identify if `web1` or `web2` is currently running.
Able to switch between web1 and web2 and kill old one appropriately, even if hotswap.sh is run multiple times.
Any image can be specified, not just activityold or activitynew.
