// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  firebaseConfig : {
    apiKey: "AIzaSyA1vRwVJ29Bh_HhQXiL-VPqFJlwD4nkl0U",
    authDomain: "web-notifications-53dec.firebaseapp.com",
    databaseURL: "https://web-notifications-53dec-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "web-notifications-53dec",
    storageBucket: "web-notifications-53dec.appspot.com",
    messagingSenderId: "536047723421",
    appId: "1:536047723421:web:c73d667220b3955c038574"
  },
  mapbox: {
    accessToken: 'pk.eyJ1IjoiYW1pbmUxNCIsImEiOiJjbDJkZmExazIwMGNoM2NyMXk2cW5wdXdwIn0.GRYBHFqp1puXjq7Z8PepCQ'
  }
};

/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
