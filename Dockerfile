FROM thyrlian/android-sdk:2.6

ENV APP_DIR /src/app/

RUN mkdir -p $APP_DIR

WORKDIR $APP_DIR

COPY . .

ENTRYPOINT ["bash"]

# If we don't exclude the tests, they get run during the build
# We want all the dependencies installed (and even an initial build)
# But we don't wanna run the tests since they fail initially
RUN ["./gradlew", "build", "-x", "test"]
