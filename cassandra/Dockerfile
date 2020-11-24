FROM cassandra:4.0

COPY --chown=root:root ./entrypoint-wrap.sh /root/entrypoint-wrap.sh
ENTRYPOINT ["/root/entrypoint-wrap.sh"]

CMD ["cassandra", "-f"]