package com.margoni.surfingspots.data

class StubImageUrlDataSourceImpl: CityImageUrlDataSource {

    override fun imageFor(city: String): String {
        return imageUrlMap[city] ?: defaultImageUrl
    }

    private val imageUrlMap = mapOf(
        "Cuba" to "https://www.info-turismo.it/wp-content/uploads/2021/01/shutterstock_1020405811.jpg",
        "Los Angeles" to "https://www.esl.it/sites/default/files/styles/image_gallery/public/city/esl-languages-usa-los-angeles-hero.jpg?itok=GNFHWcI7",
        "Miami" to "https://content.r9cdn.net/rimg/dimg/17/74/0ca6e469-city-30651-1632b88f203.jpg?width=1750&height=1000&xhint=2635&yhint=1507&crop=true",
        "Porto" to "https://d2bgjx2gb489de.cloudfront.net/gbb-blogs/wp-content/uploads/2020/02/09192305/La-vista-panoramica-sul-Centro-storico-di-Porto.jpg",
        "Ortona" to "https://turismo.abruzzo.it/wp-content/uploads/sites/118/ortona-hd.jpg",
        "Riccione" to "https://www.dovedormire.info/wp-content/uploads/sites/119/riccione.jpg",
        "Midgar" to "https://www.everyeye.it/public/immagini/11042020/midgar.jpg"
    )

    private val defaultImageUrl = "https://www.visitlagunabeach.com/imager/images/placeholder_59451ff4b79983e7a72936324fcfd196.jpg"
}