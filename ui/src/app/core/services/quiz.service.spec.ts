import {getTestBed, TestBed} from '@angular/core/testing';

import { QuizService } from './quiz.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('QuizService', () => {
  let injector: TestBed;
  let service: QuizService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuizService]
    });
    injector = getTestBed();
    service = injector.get(QuizService);
    httpMock = injector.get(HttpTestingController);
  });
  afterEach(() => {
    httpMock.verify();
  });
  it('should be created', () => {
    const service: QuizService = TestBed.get(QuizService);
    expect(service).toBeTruthy();
  });
});
