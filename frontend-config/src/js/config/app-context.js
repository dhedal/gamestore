
import {AppMenu} from "../app.js";


class AppContext {
    appMenu;
    currentPage;

    constructor() {
    }

    set currentPage( currentPage){
        if(currentPage) this.currentPage = currentPage;
    }

    async refresh() {
        if(this.appMenu) this.appMenu.checkAuthData();
        console.log(this.currentPage);
        await this.currentPage.refresh();

    }
}

export const appContext = new AppContext();