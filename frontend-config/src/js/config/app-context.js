

class AppContext {
    appMenu;
    currentPage

    constructor() {
    }

    set currentPage( currentPage){
        if(currentPage) this.currentPage = currentPage;
    }

    refresh() {
        this.currentPage.refresh();
        if(this.appMenu) this.appMenu.checkAuthData();
    }
}

export const appContext = new AppContext();