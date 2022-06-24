package com.margoni.surfingspots.data

import com.margoni.surfingspots.domain.model.City

class CityDataSourceImpl : CityDataSource {

    private val cityList = listOf(
        City(
            "Cuba",
            "https://www.info-turismo.it/wp-content/uploads/2021/01/shutterstock_1020405811.jpg"
        ),
        City(
            "Los Angeles",
            "https://www.esl.it/sites/default/files/styles/image_gallery/public/city/esl-languages-usa-los-angeles-hero.jpg?itok=GNFHWcI7"
        ),
        City(
            "Miami",
            "https://content.r9cdn.net/rimg/dimg/17/74/0ca6e469-city-30651-1632b88f203.jpg?width=1750&height=1000&xhint=2635&yhint=1507&crop=true"
        ),
        City(
            "Porto",
            "https://d2bgjx2gb489de.cloudfront.net/gbb-blogs/wp-content/uploads/2020/02/09192305/La-vista-panoramica-sul-Centro-storico-di-Porto.jpg"
        ),
        City("Ortona", "https://turismo.abruzzo.it/wp-content/uploads/sites/118/ortona-hd.jpg"),
        City("Riccione", "https://www.dovedormire.info/wp-content/uploads/sites/119/riccione.jpg"),
        City("Midgar", "https://www.everyeye.it/public/immagini/11042020/midgar.jpg"),
    )

    override suspend fun list(): List<City> {
        return cityList
    }

}