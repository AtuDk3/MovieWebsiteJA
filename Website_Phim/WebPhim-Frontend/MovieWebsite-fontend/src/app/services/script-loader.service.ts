import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ScriptLoaderService {
  private renderer: Renderer2;

  constructor(rendererFactory: RendererFactory2) {
    this.renderer = rendererFactory.createRenderer(null, null);
  }

  loadScript(src: string, attributes: { [key: string]: any } = {}): void {
    const script = this.renderer.createElement('script');
    script.src = src;
    Object.keys(attributes).forEach(key => {
      script.setAttribute(key, attributes[key]);
    });
    this.renderer.appendChild(document.body, script);
  }
}
