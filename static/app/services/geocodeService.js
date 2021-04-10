Vue.component("geocodeService", {
    template: `
    <div>

    </div>
    `,

    data: function() {
        return {
            // sr_Latn
            // sr_RS
            // https://zeljko.popivoda.com/medjunarodne-oznake-za-srpski-jezik/
            baseUrl: "https://geocode-maps.yandex.ru/1.x/?apikey=daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a&lang=sr_Latn&format=json",
            
            config: {
                headers: {
                    Authorization: "NONE"
                }
            }
        };
    },

    methods: {
        formLocationUrl: function(street, number, city) {
            const streetSplit = street.split(" ");
            const citySplit   = city.split(" ");
            
            let locationUrl = "";
            streetSplit.forEach(element => {
                locationUrl += element + "+"
            });

            locationUrl += number + "+"
            
            citySplit.forEach(element => {
                locationUrl += element + "+"
            });

            // Remove trailing +
            if (locationUrl.length != 0) {
                locationUrl = locationUrl.slice(0, -1);
            }

            return locationUrl;
        },

        geocode: function(street, number, city, successCallback, errorCallback) {
            const locationUrl = this.formLocationUrl(street, number, city);

            const url = `${this.baseUrl}&geocode=${locationUrl}`;
            axios
                .get(url, this.config)
                .then(response => { 
                    const coordinates = this.parseGeocodeResponse(response, street, number, city);
                    successCallback(coordinates); 
                })
                .catch(error => { errorCallback(error); });

        },

        inverseGeocode: function(latitude, longitude, successCallback, errorCallback) {
            const url = `${this.baseUrl}&geocode=${longitude},${latitude}`;
            axios
                .get(url, this.config)
                .then(response => { 
                    const coordinates = this.parseInverseGeocodeResponse(response);
                    successCallback(coordinates); 
                })
                .catch(error => { errorCallback(error); });
        },

        parseGeocodeResponse: function(geocodeResponse, street, number, city) {
            try {
                const geoCollection = geocodeResponse.data.response.GeoObjectCollection;
                const metadata = geoCollection.metaDataProperty.GeocoderResponseMetaData;

                if (metadata.found == "0") {
                    return {
                        longitude: 0,
                        latitude:  0
                    };
                }
                
                const results = geoCollection.featureMember;
                console.log(results);

                let chosenResult = {};
                var i = 0;
                for (i = 0; i < results.length; i++) {
                    const element = results[i];
                    // element.GeoObject.description.includes(city)
                    if (element.GeoObject.description.indexOf(city) !== -1) {
                        chosenResult = element.GeoObject;
                        break;
                    }
                }

                // No results found
                if (i === results.length) {
                    return {
                        longitude: 0,
                        latitude:  0
                    };
                }
                
                
                console.log(chosenResult);
                const chosenResultMedatada = chosenResult.metaDataProperty.GeocoderMetaData;
                const precision = chosenResultMedatada.precision
                
                const coordsStr = chosenResult.Point.pos;
                const coordsArray = coordsStr.split(" ");

                const coordinates = {
                    longitude: Number(coordsArray[0]),
                    latitude:  Number(coordsArray[1])
                }
    
                return coordinates;

            } catch(error) {
                console.log(error);
                return {
                    longitude: 0,
                    latitude:  0
                };
            }
        },

        parseInverseGeocodeResponse: function(inverseGeocodeResponse) {
            try {
                const geoCollection = inverseGeocodeResponse.data.response.GeoObjectCollection;
                const metadata = geoCollection.metaDataProperty.GeocoderResponseMetaData;
                
                if (metadata.found == "0") {
                    return {
                        street: "",
                        number: 0,
                        city: ""
                    };
                }
                
                
                const results = geoCollection.featureMember;
                console.log(results);
                
                
                const chosenResult = results[0];
                console.log(chosenResult);
                const addressArray = chosenResult.GeoObject.metaDataProperty.GeocoderMetaData.Address.Components;

                // Convert adressArray to object
                let addressObject = {};
                addressArray.forEach(address => {
                    addressObject[address.kind] = address.name;
                });

                const number = addressObject.house != null ? Number(addressObject.house)  : 0;
                const street = addressObject.street != null ? addressObject.street : "";
                const city   = addressObject.locality != null ? addressObject.locality : "";
               
                return {
                    street: street,
                    number: number,
                    city: city
                };


            } catch(error) {
                console.log(error);
                return {
                    street: "",
                    number: 0,
                    city: ""
                };
            }
        }
    },

    mounted() {},

    destroyed() {
        // https://geocode-maps.yandex.ru/1.x/?apikey=daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a&geocode=19.814125583798077,45.25898466136606&lang=en_US
        // https://geocode-maps.yandex.ru/1.x/?apikey=daf8ca4b-3c4e-4396-9bff-8c6b22f7e69a&geocode=Koste+Racina+26+Novi+Sad&lang=en_US
    }
});
