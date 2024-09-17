import { Toast } from 'bootstrap';
export class MessageUtils {
    static show(message = null, toastContainer = "toastContainer") {
        if(message == null) return;
        const parent = document.getElementById(toastContainer);

        const div = document.createElement("div");
        div.classList.add("toast");
        div.role = "alert";
        div.ariaLive = "assertive";
        div.ariaAtomic = "true";
        div.innerHTML = `
        <div class="toast-header">
            <strong class="me-auto">Erreur</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body text-bg-danger">${message}</div>
        `;

        parent.appendChild(div);
        div.addEventListener("hidden.bs.toast", () => {
            parent.removeChild(div);
        });
        const toast = new Toast(div);
        if(toast) toast.show();
    }
}