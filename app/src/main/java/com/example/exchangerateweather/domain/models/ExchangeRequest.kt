package com.example.exchangerateweather.domain.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root


data class ExchangeDataSet(
    @field:Element(name = "Datos_de_INGC011_CAT_INDICADORECONOMIC")
    @param:Element(name = "Datos_de_INGC011_CAT_INDICADORECONOMIC")
    var data: String? = null,
)

@Root(name = "diffgram", strict = false)
@NamespaceList(
    Namespace(reference = "urn:schemas-microsoft-com:xml-msdata", prefix = "msdata"),
    Namespace(reference = "urn:schemas-microsoft-com:xml-diffgram-v1", prefix = "diffgr")
)
data class ExchangeRequest(
    @field:Element(name = "Datos_de_INGC011_CAT_INDICADORECONOMIC")
    @param:Element(name = "Datos_de_INGC011_CAT_INDICADORECONOMIC")
    var data: String? = null,
)
