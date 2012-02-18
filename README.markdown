# MiniMam
MiniMam is a prototype (read: don't use this in production) media asset management webapp, which exposes URL's to scale, crop and otherwise manage image files.

## API

* Absolute scaling, providing the new width and height
      /mam/350x263/pork.jpg

* Relative scaling, e.g. creating an image half the size of the original
  /mam/0.5/pork.jpg

* Cropping the image by providing the top left coordinates and cropped bounds
  /mam/400,200-600x500/pork.jpg

* Scaling and cropping the image in one operation
  /mam/700x525/200,100-350x250/pork.jpg

* Just output an image
  /mam/pork.jpg
  
* Folders inside the configured srcFolder are allowed
  /mam/0.5/folder/pork.jpg	

## Configuration	

There is a mam-config.txt in src/main/resources. Please check this file for inline documentation of available configuration options. 

## Execution

You may use the provided Maven Jetty plugin or deploy it as a war.

	mvn jetty:run

Providing logging configuration:

	mvn -Dlog4j.configuration=file:./target/classes/log4j.properties jetty:run

## Known issues 

* So far only jpg and png are supported
* There's probably some performance and security related problems

## To Do's

* List all images
* Support gifs
* Support CDN's and image upload
