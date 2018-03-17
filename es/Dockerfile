FROM openjdk:8-jre

ENV ELASTICSEARCH_VERSION 6.2.2
ENV ELASTICSEARCH_DEB_VERSION 6.2.2

ARG user=elasticsearch
ARG group=elasticsearch
ARG uid=1000
ARG gid=1000

RUN groupadd -g ${gid} ${group} \
    && useradd -d "/usr/share/elasticsearch" -u ${uid} -g ${gid} -m -s /bin/bash ${user}


RUN wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-$ELASTICSEARCH_VERSION.tar.gz && \
    tar zxvf elasticsearch-$ELASTICSEARCH_VERSION.tar.gz && \
    cp -rf elasticsearch-$ELASTICSEARCH_VERSION/. /usr/share/elasticsearch && \
    rm -rf elasticsearch-$ELASTICSEARCH_VERSION.tar.gz


#RUN set -x \
#	&& apt-get update && apt-get install -y --no-install-recommends apt-transport-https && rm -rf /var/lib/apt/lists/* \
#	&& echo 'deb https://artifacts.elastic.co/packages/6.x/apt stable main' > /etc/apt/sources.list.d/elasticsearch.list



#RUN set -x \
#	\
#	&& dpkg-divert --rename /usr/lib/sysctl.d/elasticsearch.conf \
#	\
#	&& apt-get update \
#	&& apt-get install -y --allow-unauthenticated --no-install-recommends "elasticsearch=$ELASTICSEARCH_DEB_VERSION" \
#	&& rm -rf /var/lib/apt/lists/*

ENV PATH /usr/share/elasticsearch/bin:$PATH


WORKDIR /usr/share/elasticsearch



RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v$ELASTICSEARCH_VERSION/elasticsearch-analysis-ik-$ELASTICSEARCH_VERSION.zip

#USER elasticsearch

RUN set -ex \
	&& for path in \
		./data \
		./logs \
		./config \
		./config/scripts \
	; do \
		mkdir -p "$path"; \
		chown -R elasticsearch:elasticsearch "$path"; \
	done

COPY config ./config

VOLUME /usr/share/elasticsearch/data

COPY docker-entrypoint.sh /

EXPOSE 9200 9300
ENTRYPOINT ["/docker-entrypoint.sh"]


CMD ["elasticsearch"]
