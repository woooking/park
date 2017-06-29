package com.github.woooking.elective.util

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaType
import akka.http.scaladsl.model.MediaTypes._
import play.twirl.api.{Html, Txt, Xml}

object TwirlSupport extends TwirlSupport

trait TwirlSupport {

	implicit val twirlHtmlMarshaller = twirlMarshaller[Html](`text/html`)

	implicit val twirlTxtMarshaller = twirlMarshaller[Txt](`text/plain`)

	implicit val twirlXmlMarshaller = twirlMarshaller[Xml](`text/xml`)

	protected def twirlMarshaller[A <: AnyRef: Manifest](contentType: MediaType): ToEntityMarshaller[A] = Marshaller.StringMarshaller.wrap(contentType)(_.toString)

}
