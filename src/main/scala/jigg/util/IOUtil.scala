package jigg.util

/*
 Copyright 2013-2015 Hiroshi Noji

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

import java.io._
import java.net.URL
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

object IOUtil {
  def openBinIn(path: String, gzipped: Boolean = false): ObjectInputStream =
    oiStream(inStream(path, gzipped))
  def openBinIn(in: InputStream): ObjectInputStream = oiStream(in)

  def openZipBinIn(in: InputStream) = openBinIn(new GZIPInputStream(in))

  private[this] def oiStream(in: InputStream) =
    new ObjectInputStream(new BufferedInputStream(in))

  def openBinOut(path: String): ObjectOutputStream =
    new ObjectOutputStream(new BufferedOutputStream(outStream(path)))

  def openIn(path: String): BufferedReader = bufReader(inStream(path))
  def openOut(path: String): BufferedWriter = bufWriter(outStream(path))

  def openStandardIn: BufferedReader = bufReader(System.in)
  def openStandardOut: BufferedWriter = bufWriter(System.out)

  def inStream(path: String, gzipped: Boolean = false) =
    if (path.endsWith(".gz") || gzipped) new GZIPInputStream(new FileInputStream(path))
    else new FileInputStream(path)

  def outStream(path: String) =
    if (path.endsWith(".gz")) new GZIPOutputStream(new FileOutputStream(path))
    else new FileOutputStream(path)

  def bufReader(stream: InputStream) = new BufferedReader(new InputStreamReader(stream))
  def bufWriter(stream: OutputStream) = new BufferedWriter(new OutputStreamWriter(stream))

  def openIterator(path: String): Iterator[String] = inputIterator(openIn(path))
  def openStandardIterator: Iterator[String] = inputIterator(openStandardIn)
  def inputIterator(reader: BufferedReader) = Iterator.continually(reader.readLine()).takeWhile(_ != null)

  /** Open the readable resource, and automatically close it when finishing.
    * Similar to `using` in python.
    */
  def reading[A](path: String)(f: Reader=>A) = {
    val in = openIn(path)
    f(in)
    in.close()
  }

  /** Open the writable resource, and automatically close it when finishing.
    */
  def writing[A](path: String)(f: Writer=>A) = {
    val out = openOut(path)
    f(out)
    out.flush
    out.close()
  }

  def findResource(path: String): URL =
    Thread.currentThread.getContextClassLoader.getResource(path)

  /** Open a resource on the `path` as an `InputStream`.
    *
    * `path` should be the relative path in the currently loaded jar,
    * e.g., `a/b/c` when it is on `jar:file:xxx.jar!/a/b/c`
    */
  def openResource(path: String, gzipped: Boolean = false): InputStream = {
    val loader = Thread.currentThread.getContextClassLoader
    val stream = loader.getResourceAsStream(path)
    if (path.endsWith(".gz") || gzipped) new GZIPInputStream(stream)
    else stream
  }

  def openResourceAsObjectStream(path: String, gzipped: Boolean = false): ObjectInputStream =
    openBinIn(openResource(path, gzipped))

  def openResourceAsReader(path: String, gzipped: Boolean = false): BufferedReader =
    bufReader(openResource(path, gzipped))
}
