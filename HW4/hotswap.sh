#/bin/bash
#Hotswaps between 2 dockers for nginx

PREV=$(docker ps -a -f "name=web" | grep -oh "\w*web\w")

if [ "$PREV" == "web1" ]
then
    NEW="web2"
    SWAP="swap2.sh"
elif [ "$PREV" == "web2" ]
then
    NEW="web1"
    SWAP="swap1.sh"
fi

echo "Running image: $1 as $NEW"
docker run -d -P --network ecs189_default --name $NEW $1

sleep 2 && docker exec ecs189_proxy_1 /bin/bash /bin/$SWAP
echo "Swapped with $SWAP"

docker rm -f $PREV
echo "Killed $PREV"
echo "Done!"
