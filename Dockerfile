FROM asciidoctor/docker-asciidoctor:latest as builder

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app
ENV VERSION=6.8.0
ENV DOC_PATH=/app/cn
ENV BASE_PATH=/app/base
ENV OUT_PATH=/out
COPY asciidoc/base $BASE_PATH
COPY asciidoc/cn $DOC_PATH


RUN asciidoctor -a stylesheet=$BASE_PATH/css.css -o $OUT_PATH/index.html $DOC_PATH/$VERSION/index.asciidoc && \
    rm -rf $OUT_PATH/images && \
    mkdir $OUT_PATH/images && \
    cp -r $BASE_PATH/images $OUT_PATH/images

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /out/index.html /usr/share/nginx/html
COPY --from=builder /out/images /usr/share/nginx/html

CMD [ "nginx", "-g", "daemon off;"]




#COPY framework ./framework
#COPY base ./base
#RUN asciidoctor -a stylesheet=../base/css.css -o ./index.html ./framework/index.adoc
#
#FROM nginx:alpine
#RUN rm -rf /usr/share/nginx/html/*
#COPY --from=builder /app/index.html /usr/share/nginx/html
#COPY ./framework/image /usr/share/nginx/html/image
#
#CMD [ "nginx", "-g", "daemon off;"]

