

class AppContext {
    currentPage

    constructor() {

    }

    set currentPage( currentPage){
        if(currentPage) this.currentPage = currentPage;
    }

    refresh() {
        this.currentPage.refresh();
    }
}

export const appContext = new AppContext();