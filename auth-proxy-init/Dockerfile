FROM alpine
RUN apk add iptables
COPY init.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/init.sh
ENTRYPOINT ["init.sh"]
