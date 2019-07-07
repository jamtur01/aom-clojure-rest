clean:
	rm -rf target

build: clean
	@lein compile
	@lein uberjar
	@docker build -t jamtur01/tornado-apix-jaeger .
	@docker push jamtur01/tornado-apix-jaeger

