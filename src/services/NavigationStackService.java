package services;

import java.util.Stack;

public class NavigationStackService {

    public final Stack<Integer> menuPages = new Stack<>();
    private final PageService pageService;

    protected NavigationStackService(PageService pageService) {
        this.pageService = pageService;
    }

    protected Stack<Integer> getMenuPages() {
        return menuPages;
    }

    protected final void goBack(int pageId) {
        int lastPageId;
        do {
            lastPageId = getMenuPages().pop();
        } while (lastPageId == pageId && !getMenuPages().isEmpty());

        pageService.getPageById(lastPageId).displayAllPage();
    }
}
