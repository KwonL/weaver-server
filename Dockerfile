FROM gradle:jre11

LABEL maintainer="lkh116@snu.ac.kr"

WORKDIR /root

ADD gradle gradle
ADD build.gradle .
ADD settings.gradle .
RUN gradle fetchDeps --no-daemon

ADD src src
RUN gradle build --no-daemon

CMD java -Dspring.profiles.active=production -jar weaver.jar
