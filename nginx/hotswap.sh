#/bin/bash
#Hotswaps between 2 dockers for nginx
#Does not error check for if specified image is already running

echo "Running image: $1"

if [ "$1" == "activitynew" ]
then
    PREV="web1"
    NEW="web2"
    SWAP="swap2.sh"
elif [ "$1" == "activityold" ]
then
    PREV="web2"
    NEW="web1"
    SWAP="swap1.sh"
else
    echo "Wrong package names!"
    exit 0
fi

docker run -d -P --network ecs189_default --name $NEW $1

sleep 2 && docker exec ecs189_proxy_1 /bin/bash /bin/$SWAP
echo "Swapped with $SWAP"

docker rm -f $PREV
echo "Killed $PREV"
echo "Done!"