// // src/app/directives/lazy-load.directive.ts
// import { Directive, ElementRef, Input, Renderer2, HostListener, OnInit } from '@angular/core';

// @Directive({
//   selector: '[appLazyLoad]'
// })
// export class LazyLoadDirective implements OnInit {
//   @Input('dataSrc') dataSrc: string | undefined;

//   a:number=1
//   b:number=1
//   c:number=1

//   constructor(private el: ElementRef, private renderer: Renderer2) { }

//   ngOnInit(): void {
//     console.log('LazyLoadDirective initialized');
//     const imgElement = this.el.nativeElement as HTMLImageElement;
//     this.loadImage(imgElement);
//   }

//   @HostListener('window:scroll', ['$event'])
//   onScroll(): void {
//     console.log('Scroll event detected'+this.a++);
//     const imgElement = this.el.nativeElement as HTMLImageElement;
//     this.loadImage(imgElement);
//   }

//   private loadImage(imgElement: HTMLImageElement): void {
//     console.log('Checking if image is in viewport'+this.b++);
//     const rect = imgElement.getBoundingClientRect();
//     const isInViewport = (rect.top <= window.innerHeight) && (rect.bottom >= 0);

//     if (isInViewport && this.dataSrc) {
//       console.log('Image is in viewport, loading image'+this.c++);
//       this.renderer.setAttribute(imgElement, 'src', this.dataSrc);
//     }
//   }
// }
