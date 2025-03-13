import { TestBed } from '@angular/core/testing';

import { WithTokenInterceptor } from './with-token.interceptor';

describe('WithTokenInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      WithTokenInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: WithTokenInterceptor = TestBed.inject(WithTokenInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
