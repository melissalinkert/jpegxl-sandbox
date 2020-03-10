Install Bazel 1.2.1
-------------------

See https://docs.bazel.build/versions/master/install.html
Do not install 2.x.x, it is not compatible with the brunsli build files.
On Ubuntu:

```
$ curl https://bazel.build/bazel-release.pub.gpg | sudo apt-key add -
$ echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list
$ sudo apt-get update
$ sudo apt-get install bazel-1.2.1
```

Clone and build google/brunsli
------------------------------

```
$ git clone git://github.com/google/brunsli
$ cd brunsli
$ git submodule update --init --recursive
$ mkdir bin
$ cd bin
$ cmake ..
$ make -j
$ sudo make -j install
```

Build the Java wrapper
----------------------

```
$ cd ../java
$ bazel-1.2.1 build :all
$ cd dev/brunsli/wrapper
$ bazel-1.2.1 build :all
$ cd ../../../bazel-bin/dev/brunsli/wrapper
$ jar tf libcodec.jar
META-INF/
META-INF/MANIFEST.MF
META-INF/LICENSE
dev/
dev/brunsli/
dev/brunsli/wrapper/
dev/brunsli/wrapper/Codec.class
dev/brunsli/wrapper/CodecJNI.class
$ export CLASSPATH=$CLASSPATH:`pwd`/libcodec.jar
$ cp ../../../brunsli_jni.dll $JPEGXL-SANDBOX/libbrunsli_jni.so
```

Build the test class
--------------------

```
$ export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:`pwd`
$ javac JPEGXLTest.java
```

Convert JPEG to JPEG-XL
-----------------------

```
$ java -mx1g JPEGXLTest encode input.jpg output-xl.jxl
```

Convert JPEG-XL to JPEG
-----------------------

```
$ java -mx1g JPEGXLTest decode output-xl.jxl roundtrip.jpg
```

Results on sample images
------------------------

See ```bf-data-repo/automated-tests/curated/jpeg/big-images/```

|File   | Input size  |  Output size |  Encode time |  Decode time |
|-------|-------------|--------------|--------------|--------------|
| 2kx2k | 497926      |  140048      |  0.905s      |  0.907s      |
| 4kx4k | 1816405     |  534007      |  2.794s      |  1.696s      |
| 8kx8k | 6302116     |  1768975     |  6.712s      |  7.143s      |

Compressed output file size is 28-30% of input JPEG file size.
MD5 of original JPEG is equal to MD5 of roundtripped JPEG in all test cases.

Further reading
---------------

https://arxiv.org/ftp/arxiv/papers/1908/1908.03565.pdf
