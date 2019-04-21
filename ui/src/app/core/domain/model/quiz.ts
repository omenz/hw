export abstract class Entity {
  id: number;
  
  protected constructor(id: number) {
    this.id = id;
  }
}

export class Quiz extends Entity{
  name: string;
  forms: Array<QuizForm>;

  constructor(id: number, name: string, forms: Array<QuizForm>) {
    super(id);
    this.name = name;
    this.forms = forms;
  }
}

export class QuizForm extends Entity{
  language: string;
  section: QuizSection;

  constructor(id: number, language: string, section: QuizSection) {
    super(id);
    this.language = language;
    this.section = section;
  }
}

export class QuizSection extends Entity {
  childSections: Array<QuizSection>;
  quizQuestions: Array<QuizQuestion>;

  constructor(id: number, childSections: Array<QuizSection>, quizQuestions: Array<QuizQuestion>) {
    super(id);
    this.childSections = childSections;
    this.quizQuestions = quizQuestions;
  }
}

export class QuizQuestion extends Entity{
  question: string;
  updateCount: number;
  answers: Array<QuizAnswer>;

  constructor(id: number, question: string, updateCount: number, answers: Array<QuizAnswer>) {
    super(id);
    this.question = question;
    this.updateCount = updateCount;
    this.answers = answers;
  }
}

export class QuizAnswer extends Entity{
  answer: string;

  constructor(id: number, answer: string) {
    super(id);
    this.answer = answer;
  }
}
