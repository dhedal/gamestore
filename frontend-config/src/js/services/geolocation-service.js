
export class GeolocationService {
    constructor() {
    }

    static getClosestAgency(agencyMap) {
        return new Promise((resolve, reject) => {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition((position) => {
                    const userLat = position.coords.latitude;
                    const userLon = position.coords.longitude;
                    let closestAgency = null;
                    let closestDistance = Infinity;

                    agencyMap.forEach((agency) => {
                        const distance = GeolocationService.calculateDistance(userLat, userLon, agency.latitude, agency.longitude);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestAgency = agency;
                        }
                    });
                    resolve(closestAgency);
                }, (error) => {
                    reject(error);
                });
            } else {
                reject(new Error("La géolocalisation n'est pas supportée par ce navigateur"));
            }
        });
    }

    static calculateDistance(lat1, lon1, lat2, lon2){
        const R = 6371;
        const dLat = GeolocationService.deg2rad(lat2 - lat1);
        const dLon = GeolocationService.deg2rad(lon2 - lon1);
        const a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(GeolocationService.deg2rad(lat1)) * Math.cos(GeolocationService.deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        const distance = R * c; // Distance en km
        return distance;
    }

    static deg2rad(deg) {
        return deg * (Math.PI/180);
    }

}