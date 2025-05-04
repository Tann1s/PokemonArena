# Compile our java files in this container
FROM openjdk:22-slim AS builder
COPY src /usr/src/cs6310/src
WORKDIR /usr/src/cs6310
RUN find . -name "*.java" | xargs javac -d ./target
RUN jar cfe pokemon.jar Main -C ./target/ .

# Copy the jar and test scenarios into our final image
FROM openjdk:22-slim
WORKDIR /usr/src/cs6310
COPY test_scenarios ./
COPY test_results ./
COPY --from=builder /usr/src/cs6310/pokemon.jar ./pokemon.jar
CMD ["java", "-jar", "pokemon.jar"]
