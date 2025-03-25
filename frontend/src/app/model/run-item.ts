export class RunItem {
  constructor(
	public id: number,
    public name: string,
    public searchQuery: string,
	public totalResults: number,
	public status: string
  ) {}
}