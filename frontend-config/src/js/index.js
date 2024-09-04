import "bootstrap";
import "../scss/main.scss";
import { AppMenu} from "./app.js";
import {router} from "./routes/router.js";

(function() {
    router.loadPage().then(() => {
        const appMenu = new AppMenu();
    });


})();