#!/bin/sh

# Redirect all TCP traffic from the resource-server (port 80) to the auth-proxy (port 8000)
iptables -t nat -A PREROUTING -p tcp -i eth0 --dport 80 -j REDIRECT --to-port 8000

# DEBUG - Bypass the auth-proxy by opening a debug port (port 9000) to the resource server (port 80)
# iptables -t nat -A PREROUTING -p tcp -i eth0 --dport 9000 -j REDIRECT --to-port 80
