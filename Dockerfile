FROM runmymind/docker-android-sdk

ENV APP_DIR /src/app/

RUN mkdir -p $APP_DIR

ADD ./CandyCoded/app/build.gradle ${APP_DIR}/CandyCoded

WORKDIR $APP_DIR

COPY . .

RUN ./CandyCoded/gradlew --parallel testDebug -p */app | :

CMD ["sh"]
